(ns nubank-alura-onboarding.core-test
  (:require [clojure.test :refer :all]
            [nubank-alura-onboarding.core :refer :all]
            [clojure.test.check.clojure-test :refer :all]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [schema.core :as s]
            [schema-generators.generators :as g]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))
