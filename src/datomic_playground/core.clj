(ns datomic-playground.core
  (:require [datomic.ion.lambda.api-gateway :as api-gateway]
   :gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn get-equipment-handler [{:keys [header body]}]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello World!"})

(def get-equipment
  (:api-gateway/ionize get-equipment-handler))
  


;======================================================
;Some usefull stuff for REPL interaction with Datomic

(require '[datomic.client.api :as d])

(def cfg {:server-type :cloud
          :region "us-east-1"
          :system "jdb-datomic-dev"
          :query-group "jdb-datomic-dev"
          :endpoint "http://entry.jdb-datomic-dev.us-east-1.datomic.net:8182/"
          :proxy-port 8182})

(def client (d/client cfg))

;Find all database names

(d/list-databases client {})

;Connect to a database

(def conn (d/connect client {:db-name "test"}))

; Schema

(def eqService-schema [{:db/ident :eqService/id
                        :db/valueType :db.type/uuid
                        :db/cardinality :db.cardinality/one
                        :db/unique :db.unique/identity
                        :db/doc "Unique external IDs"}
                       
                       {:db/ident :equipment/equipmentName
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "The name of the equipment"}
                       
                       {:db/ident :equipment/description
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "Equipment description"}
                       
                       {:db/ident :equipment/isActive
                        :db/valueType :db.type/boolean
                        :db/cardinality :db.cardinality/one
                        :db/doc "Is the equipment active?"}
                       
                       {:db/ident :equipment/location
                        :db/valueType :db.type/ref
                        :db/cardinality :db.cardinality/one
                        :db/doc "The current location of the equipment"}
                       
                       {:db/ident :equipment/lastKnownCoordinates
                        :db/valueType :db.type/ref
                        :db/cardinality :db.cardinality/one
                        :db/doc "The last know latitude, longitdue, and timestamp of the equipment"}
                       
                       {:db/ident :equipment/equipmentMake
                        :db/valueType :db.type/ref
                        :db/cardinality :db.cardinality/one
                        :db/doc "The manufacturer of the equipment"}
                       
                       {:db/ident :equipment/equipmentModel
                        :db/valueType :db.type/ref
                        :db/cardinality :db.cardinality/one
                        :db/doc "The model of the equipment"}
                       
                       {:db/ident :equipment/meter
                        :db/valueType :db.type/ref
                        :db/cardinality :db.cardinality/one
                        :db/doc "The equipment meter"}
                       
                       {:db/ident :equipment/equipmentType
                        :db/valueType :db.type/ref
                        :db/cardinality :db.cardinality/one
                        :db/doc "The equipment type"}
                       
                       {:db/ident :equipment/equipmentSubType
                        :db/valueType :db.type/ref
                        :db/cardinality :db.cardinality/one
                        :db/doc "The equipment subtype"}
                       
                       {:db/ident :equipment/serialNumber
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "The serial number of the equipment"}
                       
                       {:db/ident :equipment/purchaseDate
                        :db/valueType :db.type/instant
                        :db/cardinality :db.cardinality/one
                        :db/doc "The date of purchase of the equipment"}
                       
                       {:db/ident :equipment/vendor
                        :db/valueType :db.type/ref
                        :db/cardinality :db.cardinality/one
                        :db/doc "The vendor the equipment was purchased from"}
                       
                       {:db/ident :equipment/purchasePrice
                        :db/valueType :db.type/bigdec
                        :db/cardinality :db.cardinality/one
                        :db/doc "The purchase price of the equipment"}
                       
                       {:db/ident :equipment/year
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "The year the equipment was manufactured"}
                       
                       {:db/ident :equipment/assignedTo
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "Primary user of equipment if applicable"}
                       
                       {:db/ident :equipment/disposalDate
                        :db/valueType :db.type/instant
                        :db/cardinality :db.cardinality/one
                        :db/doc "Date equipment was retired/sold/etc"}
                       
                       {:db/ident :equipment/disposalNote
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "Note about equipment retirement/sale/etc"}
                       
                       {:db/ident :equipmentMake/makeName
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/unique :db.unique/identity
                        :db/doc "Equipment Make name"}
                       
                       {:db/ident :equipmentMake/models
                        :db/valueType :db.type/ref
                        :db/cardinality :db.cardinality/many
                        :db/doc "Equipment models for this make"}
                       
                       {:db/ident :equipmentModel/modelName
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/unique :db.unique/identity
                        :db/doc "Name of equipment model"}
                       
                       {:db/ident :equipmentType/typeName
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/unique :db.unique/identity
                        :db/doc "Equipment type name"}
                       
                       {:db/ident :equipmentType/subtypes
                        :db/valueType :db.type/ref
                        :db/cardinality :db.cardinality/many
                        :db/doc "Equipment subtypes for this type"}
                       
                       {:db/ident :equipmentSubType/name
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/unique :db.unique/identity
                        :db/doc "Name of equipment subtype"}
                       
                       {:db/ident :equipmentLocation/name
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "Name of potential equipment location/job"}
                       
                       {:db/ident :spatialCoordinates/latitude
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "Latitude of coordinates"}
                       
                       {:db/ident :spatialCoordinates/longitude
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "Longitude of coordinates"}
                       
                       {:db/ident :spatialCoordinates/timestamp
                        :db/valueType :db.type/instant
                        :db/cardinality :db.cardinality/one
                        :db/doc "Timestamp of coordinate measurement"}])
                       
 ;;some test data

(def eqTypesAndSubtypes [{:equipmentType/typeName "Dozer"}
                         {:equipmentType/typeName "Pickup"}
                         {:equipmentType/typeName "Scraper"}
                         {:equipmentType/typeName "Dump"
                          :equipmentType/subtypes [{:equipmentSubType/name "Articulated"}]}
                         {:equipmentType/typeName "Crane"
                          :equipmentType/subtypes [{:equipmentSubType/name "Rough Terrain"}
                                                   {:equipmentSubType/name "Crawler"}
                                                   {:equipmentSubType/name "Telescopic Truck"}]}])

(def eqMakesAndModels [])

;;Some queries..

;;List all equipment type names
(def all-typesNames-q '[:find ?eqType 
                        :where [_ :equipmentType/typeName ?eqType]])

;;List all equipment types
(def all-types-q '[:find (pull ?e [*])
                   :where [?e :equipmentType/typeName]])

;;results of all-types-q
[[{:db/id 45484597017837666, 
   :equipmentType/typeName "Crane", 
   :equipmentType/subtypes [#:db{:id 12182588835758181} #:db{:id 57680379993129060} #:db{:id 62566609666965603}]}]
 [{:db/id 13057800091467869, 
   :equipmentType/typeName "Dozer"}]
 [{:db/id 3153399348461664, 
   :equipmentType/typeName "Dump", 
   :equipmentType/subtypes [#:db{:id 57302147993174113}]}] 
 [{:db/id 30227773670817886, :equipmentType/typeName "Pickup"}] 
 [{:db/id 37598899623428191, :equipmentType/typeName "Scraper"}]]

;;2nd attempt at listing all equipment types - trying to grab equipmentSubType/names too...
(def all-types2-q '[:find (pull ?e [:equipmentType/typeName
                                    {:equipmentType/subtypes [:equipmentSubType/name]}])
                    :where [?e :equipmentType/typeName]])
;;results of all-types2-q
[[#:equipmentType{:typeName "Crane", 
                  :subtypes [#:equipmentSubType{:name "Telescopic Truck"} 
                             #:equipmentSubType{:name "Crawler"} 
                             #:equipmentSubType{:name "Rough Terrain"}]} 
  [#:equipmentType{:typeName "Dozer"}] 
  [#:equipmentType{:typeName "Dump", 
                   :subtypes [#:equipmentSubType{:name "Articulated"}]}] 
  [#:equipmentType{:typeName "Pickup"}] 
  [#:equipmentType{:typeName "Scraper"}]]]

;;Fix REPL formatting:
;;(set! print-namespace-maps false) to fix REPL formatting
;;MUCH better!

[[{:equipmentType/typeName "Crane", 
   :equipmentType/subtypes [{:equipmentSubType/name "Telescopic Truck"} 
                            {:equipmentSubType/name "Crawler"} 
                            {:equipmentSubType/name "Rough Terrain"}]}] 
 [{:equipmentType/typeName "Dozer"}] 
 [{:equipmentType/typeName "Dump", 
   :equipmentType/subtypes [{:equipmentSubType/name "Articulated"}]}] 
 [{:equipmentType/typeName "Pickup"}] 
 [{:equipmentType/typeName "Scraper"}]]

;; run query

;; db shorthand
(def db (d/db conn))


(d/q all-typeNames-q db)
              
                        
                       
                       

                       
                       



                  







                       
                       
                       
                       
                       
                       
                       





