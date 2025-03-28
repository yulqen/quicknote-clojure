(ns yulqen.quicknote-test
  (:require
    [clojure.test :refer :all]
    [yulqen.quicknote :refer :all]))


(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest markdown-link
  (testing "Ensure text can be converted to markdown."
    (is (format-markdown-link "Yonkers" "https://example.com")
        "- [Yonkers](https://example./com")))
