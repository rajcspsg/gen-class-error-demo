(ns myorg.plugin.orc-output
  (:import
   [myorg.helpers.fs-input-stream FsInputStream]
   java.io.ByteArrayOutputStream
   java.lang.UnsupportedOperationException
   java.nio.ByteBuffer
   [java.net URL]
   [java.io File]
   [java.nio.file Path]
   org.apache.hadoop.conf.Configuration
   [org.apache.orc OrcFile]
   [org.apache.hadoop.fs FileSystem FileSystem$Statistics FSDataInputStream FSDataOutputStream]))

(set! *warn-on-reflection* true)

(defn to-path
  [x]
  {:post (instance? Path %)}
  (cond
    (instance? URL x) (Path. (.toURI ^URL x))
    (instance? File x) (Path. (.getPath ^File x))
    (string? x)                (Path. ^String x)
    (instance? Path x)         x))

(def dummy-path (to-path "dummy"))

(def orc-header-length (count OrcFile/MAGIC))

(defn input-stream-filesystem [buffered-bytes]
  (proxy [FileSystem] []
    (open [_]
      (FSDataInputStream. (FsInputStream. buffered-bytes)))))

(defn create-orc-reader [buffered-bytes]
  (OrcFile/createReader dummy-path (doto (OrcFile/readerOptions (Configuration.))
                                     (.maxLength (count buffered-bytes))
                                     (.filesystem (input-stream-filesystem buffered-bytes)))))
