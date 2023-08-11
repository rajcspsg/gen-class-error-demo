(ns myorg.plugin.orc-output
  (:import
   (myorg.helpers FsInputStream)
   (org.apache.hadoop.fs FileSystem FSDataInputStream)))

(set! *warn-on-reflection* true)


(defn input-stream-filesystem [buffered-bytes]
  (proxy [FileSystem] []
    (open [_]
      (FSDataInputStream. (FsInputStream. buffered-bytes)))))
