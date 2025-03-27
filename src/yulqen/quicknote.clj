(ns yulqen.quicknote
  (:require
   [clojure.tools.cli :refer [parse-opts]]
   [net.cgrand.enlive-html :as html])
  (:import [java.awt.datatransfer DataFlavor])
  (:gen-class))

;; Java equivalent of clipboard-text, according to ChatGPT
;;
;; import java.awt.*;
;; import java.awt.datatransfer.*;

;; public class ClipboardHelper {
;;     public static String clipboardText() {
;;         try {
;;             Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
;;             Transferable contents = clipboard.getContents(null);
;;             return (String) contents.getTransferData(DataFlavor.stringFlavor);
;;         } catch (Exception e) {
;;             return null;
;;         }
 

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
        (println "Not a URL. You need a URL on your clipboard for this to work."))
      (System/exit 1))))

(defn format-markdown-link
  "Formats text and url as a markdown link"
  [text url]
  (str "[" text "]" "(" url ")"))

(defn url-to-markdown [url]
  (format-markdown-link (get-title-text url) url))

(defn append-to-file
  ([]
   (let [url (clipboard-text)
         path "/home/lemon/Documents/Notes/quicknote.md"]
     (spit path (apply str "- " (url-to-markdown url) "\n") :append true)))
  ([comment]
   (let [url (clipboard-text)
         path "/home/lemon/Documents/Notes/quicknote.md"]
     (spit path (apply str "- " comment ": " (url-to-markdown url) "\n") :append true))))

(defn -main
  [& args]
  (if args (append-to-file (first args)) (append-to-file)))
