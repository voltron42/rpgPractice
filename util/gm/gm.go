package gm

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type GM interface {
	Narrate(narrative string)
	NarrateF(tpl string, objects ...interface{})
	Query(request string) string
	QueryInt(request string) int
}

func New() GM {
	return myGM{}
}

type myGM struct {
}

func (gm myGM) Narrate(narrative string) {
	fmt.Println(narrative)
}

func (gm myGM) NarrateF(tpl string, objects ...interface{}) {
	fmt.Println(fmt.Sprintf(tpl, objects...))
}

func (gm myGM) Query(request string) string {
	gm.Narrate(request)
	reader := bufio.NewReader(os.Stdin)
	text, _ := reader.ReadString('\n')
	text = strings.TrimSpace(text)
	return text
}

func (gm myGM) QueryInt(request string) int {
	var out *int
	for out == nil {
		response := gm.Query(request)
		value, err := strconv.Atoi(response)
		if err != nil {
			gm.Narrate(err.Error())
		} else {
			out = &value
		}
	}
	return *out
}
