package main

import (
	"fmt"
	"math/rand"
)

func (s *Stats) Update(gm GM) {
	for _, stat := range []string{"STRENGTH", "WEALTH", "MONSTER TALLY"} {
		gm.Narrate(fmt.Sprintf("%v: \t%v", stat, (*s)[stat]))
	}
	gm.Narrate("")
}

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
	stats.Update(gm)
}

func (t TestAction) DoAction(gm GM, stats *Stats) {
	actions := t.Otherwise
	for _, when := range t.When {
		if when.Test.test(stats) {
			actions = when.Actions
		}
	}
	for _, action := range actions {
		action.DoAction(gm, stats)
	}
}

func (s SimpleTestCondition) test(stats *Stats) bool {
	current := (*stats)[s.Stat]
	comparison := comparisons[s.Comparator]
	return comparison(current, s.Value)
}

var comparisons = map[Comparator]func(a, b int) bool{
	GreaterThan: func(a, b int) bool {
		return a > b
	},
	EqualTo: func(a, b int) bool {
		return a == b
	},
	LessThan: func(a, b int) bool {
		return a < b
	},
}

func (i InvertedTestCondition) test(stats *Stats) bool {
	return !i.test(stats)
}

func (a AggregatedTestCondition) test(stats *Stats) bool {
	for _, condition := range a.Conditions {
		if condition.test(stats) != bool(a.Conjunction) {
			return !bool(a.Conjunction)
		}
	}
	return bool(a.Conjunction)
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
