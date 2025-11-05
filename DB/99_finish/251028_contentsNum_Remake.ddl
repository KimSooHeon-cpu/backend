-- 기존 제약조건 (content_num 단독)
--ALTER TABLE contents_tbl ADD CONSTRAINT contents_tbl_num_un UNIQUE (content_num);
ALTER TABLE contents_tbl DROP CONSTRAINT contents_tbl_num_un;

-- 변경 후 제약조건 (contentType, contentNum 복합)
ALTER TABLE contents_tbl ADD CONSTRAINT contents_type_num_un UNIQUE (content_type, content_num);
