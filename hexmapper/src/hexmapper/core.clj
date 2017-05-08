(ns hexmapper.core
  (:require [clojure.xml :as xml]
            [clojure.edn :as edn]
            [clojure.string :as s])
  (:import
    (java.io InputStream OutputStream File FileOutputStream ByteArrayInputStream ByteArrayOutputStream)
    (org.apache.fop.apps FopFactory MimeConstants)
    (javax.xml.transform.sax SAXResult)
    (javax.xml.transform.stream StreamSource)
    (javax.xml.transform TransformerFactory))
  )

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn stringify-xml-doc [body]
  (with-out-str (xml/emit body))
  )

(defn stringify-xml-elem [elem]
  (with-out-str (xml/emit-element elem))
  )

(defn sample []
  (let [outer {:tag :hello :content ["%s"]}
        inner [{:tag :a
                :attrs {:href "#start"}
                :content [{:tag :img :attrs {:src "images/one.png"} :content nil}]}
               {:tag :a
                :attrs {:href "#end"}
                :content [{:tag :img :attrs {:src "images/two.png"} :content nil}]}]
        innerstr (s/join "" (map stringify-xml-elem inner))
        prtstr (format (stringify-xml-doc outer) innerstr)
        ]
    (println "file content: (" prtstr ")")
    )
  )

(defn map-clm [body]
  (let [bodynext (if (keyword? body) (vector body) body)
        [tag attrs & content] bodynext
        attrs (if (nil? attrs) {} attrs)
        content (if (map? attrs) content (into [attrs] content))
        attrs (if (map? attrs) attrs {})
        content (if (or (nil? content) (empty? content)) content (vec (map #(if (string? %) % (map-clm %)) content)))
        ]
    (reduce (fn [out [k v]] (if (or (empty? v) (nil? v)) out (assoc out k v))) {:tag tag} {:attrs attrs :content content})
    )
  )

(defn edn-to-xml-elem [infile outfile prefix mapper]
  (->> infile
       (slurp)
       (edn/read-string)
       (mapper)
       (stringify-xml-elem)
       (str prefix)
       (spit outfile)
       )
  )
(defn edn-to-xml-doc [infile outfile mapper]
  (->> infile
      (slurp)
      (edn/read-string)
      (mapper)
      (stringify-xml-doc)
      (spit outfile)
      )
  )

(defn fo-to-pdf [^InputStream instream ^OutputStream outstream]
  (try
    (let [fop-factory (FopFactory/newInstance (.toURI (File. ".")))
          fop (.newFop fop-factory MimeConstants/MIME_PDF outstream)
          tf-factory (TransformerFactory/newInstance)
          tf (.newTransformer tf-factory)
          src (StreamSource. instream)
          res (SAXResult. (.getDefaultHandler fop))]
      (.transform tf src res)
      )
    (catch Exception e (println e))
    (finally (.close outstream)))
  )

(defn xsl-to-pdf [^InputStream xmlstream ^InputStream xsltstream ^OutputStream outstream]
  (try
    (let [fop-factory (FopFactory/newInstance (.toURI (File. ".")))
          fo-user-agent (.newFOUserAgent fop-factory)
          fop (.newFop fop-factory MimeConstants/MIME_PDF fo-user-agent outstream)
          tf-factory (TransformerFactory/newInstance)
          tf (.newTransformer tf-factory (StreamSource. xsltstream))
          src (StreamSource. xmlstream)
          res (SAXResult. (.getDefaultHandler fop))]
      (.transform tf src res)
      )
    (catch Exception e (println e))
    (finally (.close outstream)))
  )

(defn -main [& args]
  (let [xml (ByteArrayInputStream. (.getBytes (stringify-xml-doc {:tag :root})))
        xsl (->> "resources/fo.xml" (slurp) (.getBytes) (ByteArrayInputStream.))
        out (ByteArrayOutputStream.)]
    (xsl-to-pdf xml xsl out)
    (.flush out)
    (spit "resources/fo.pdf" (.toString out))
    )
  "Completed"
  )