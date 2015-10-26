package main

import "math/rand"

func (r RandomAction) DoAction(gm GM, stats *Stats) {
	if len(r.Description) > 0 {
		gm.Narrate(r.Description)
	}
	results := r.Randomizer.Random()
	matchingIndex := -1
	for index, result := range r.Results {
		for _, resultsInstance := range result.Results {
			if resultsInstance.Match(results) {
				matchingIndex = index
				break
			}
		}
	}
	if matchingIndex > -1 {
		result := r.Results[matchingIndex]
		if len(result.Description) > 0 {
			gm.Narrate(result.Description)
		}
		for _, action := range result.Actions {
			action.DoAction(gm, stats)
		}
	}
}

func (c Coins) Random() Results {
	out := Results{}
	for index := 0; index < int(c); index++ {
		out = append(out, getRandom("heads", "tails"))
	}
	return out
}

func getRandom(value1, value2 string, addlValues ...string) string {
	values := append([]string{value1, value2}, addlValues...)
	index := rand.Intn(len(values))
	return values[index]
}

func (r Results) Match(results Results) bool {
	if len(r) != len(results) {
		return false
	}
	for index, result := range r {
		if results[index] != result {
			return false
		}
	}
	return true
}

func (c Change) DoAction(gm GM, stats *Stats) {
	if len(c.Description) > 0 {
		gm.Narrate(c.Description)
	}
	prev := (*stats)[c.Stat]
	operation := operations[c.Operator]
	newValue := operation(prev, c.Value)
	(*stats)[c.Stat] = newValue
}

var operations = map[Operator]func(a, b int) int{
	Add: func(a, b int) int {
		return a + b
	},
	Sub: func(a, b int) int {
		return a - b
	},
	Mult: func(a, b int) int {
		return a * b
	},
	Div: func(a, b int) int {
		return a / b
	},
	Set: func(a, b int) int {
		return b
	},
}
