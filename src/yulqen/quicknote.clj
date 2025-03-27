(ns yulqen.quicknote
  (:require [net.cgrand.enlive-html :as html])
  (:gen-class))

(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn get-title-text [url]
  (first (:content (first (html/select (html/html-resource (java.net.URL. url)) [:title])))))

(defn format-markdown-link
  "Formats text and url as a markdown link"
  [text url]
  (str "[" text "]" "(" url ")"))

(defn url-to-markdown [url]
  (format-markdown-link (get-title-text url) url))

(defn append-to-file [path url]
  (spit path (apply str "- " (url-to-markdown url) "\n") :append true))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))
