(ns myorg.plugin.orc-output
  (:import
   (myorg.helpers FsInputStream)
   (java.nio.file Path Paths)
   org.apache.hadoop.conf.Configuration
   (org.apache.orc OrcFile)
   (org.apache.hadoop.fs FileSystem FSDataInputStream)))

(set! *warn-on-reflection* true)

(def dummy-path (Paths/get "dummy"))

(defn input-stream-filesystem [buffered-bytes]
  (proxy [FileSystem] []
    (open [_]
      (FSDataInputStream. (FsInputStream. buffered-bytes)))))
