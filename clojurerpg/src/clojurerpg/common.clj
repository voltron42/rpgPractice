(ns clojurerpg.common)

(defn expressionDecoder [opmap]
  (fn decodeExpr [expression]
    (let
      [
       resolver
       (fn resolveExp [vars exp]
         (let
           [
            resolveArg
            (fn resolveAaarg [arg]
              (if (seq? arg)
                #(resolveExp vars arg)
                (if (symbol? arg)
                  #(vars arg)
                  (if (keyword? arg)
                    #(vars arg)
                    #(identity arg)
                    )
                  )
                )
              )
            func (opmap (first exp))
            args (map resolveArg (rest exp))
            ]
           (apply func (map #(%) args))
           )
         )
       ]
      (fn returnedFunction [varmap]
        (resolver varmap expression)
        )
      )
    )
  )
