package main

type GM interface {
	Narrate(narrative string)
	Query() string
}

type Game struct {
	Stats    *Stats
	Chapters Chapters
}

type Stats map[string]int

type Chapters map[int]Chapter

type Chapter struct {
	Description string
	Actions     []Action
	Options     []Option
}

type Option struct {
	Description string
	Route       int
}

type Action interface {
	DoAction(gm GM, stats *Stats)
}

type RandomAction struct {
	Description string
	Randomizer  Randomizer
	Results     []RandomActionResult
}

type RandomActionResult struct {
	Description string
	Results     []Results
	Actions     []Action
}

type Randomizer interface {
	Random() Results
}

type Coins int

type Results []string

type Change struct {
	Description string
	Stat        string
	Value       int
	Operator    Operator
}

type Operator int

const (
	Add Operator = iota
	Sub
	Mult
	Div
	Set
)
