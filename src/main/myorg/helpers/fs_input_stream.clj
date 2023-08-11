(ns myorg.helpers.fs-input-stream)

(gen-class
 :name "myorg.helpers.FsInputStream"
 :extends java.io.ByteArrayInputStream
 :implements [org.apache.hadoop.fs.Seekable org.apache.hadoop.fs.PositionedReadable]
 :main false
 :exposes {buf  {:get getBuf}
           pos  {:get getPos}
           mark {:get getMark}}
 :init-impl-ns false)
