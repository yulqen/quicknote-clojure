(ns yulqen.quicknote
  (:require [net.cgrand.enlive-html :as html])
  (:import [java.awt.datatransfer DataFlavor])
  (:gen-class))

(defn
 clipboard-text
 []
 (try
  (.getTransferData
   (.getContents
    (.getSystemClipboard (java.awt.Toolkit/getDefaultToolkit))
    nil)
   (DataFlavor/stringFlavor))
  (catch java.lang.NullPointerException e nil)))

(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn get-title-text [url]
  (try
    (first (:content (first (html/select (html/html-resource (java.net.URL. url)) [:title]))))
    (catch java.net.MalformedURLException e
      (do
        (println "Not a URL")) nil)))

(defn format-markdown-link
  "Formats text and url as a markdown link"
  [text url]
  (str "[" text "]" "(" url ")"))

(defn url-to-markdown [url]
  (format-markdown-link (get-title-text url) url))

(defn append-to-file []
  (let [url (clipboard-text)
        path "/home/lemon/Documents/Notes/quicknote.md"]
    (spit path (apply str "- " (url-to-markdown url) "\n") :append true)))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))
