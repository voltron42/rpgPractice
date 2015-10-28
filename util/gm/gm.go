package gm

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

type GM interface {
	Narrate(narrative string)
	Query() string
}

func New() GM {
	return myGM{}
}

type myGM struct {
}

func (gm myGM) Narrate(narrative string) {
	fmt.Println(narrative)
}

func (gm myGM) Query() string {
	reader := bufio.NewReader(os.Stdin)
	text, _ := reader.ReadString('\n')
	text = strings.TrimSpace(text)
	return text
}
