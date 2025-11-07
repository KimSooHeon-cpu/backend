-- selectFacilities
-- Oracle 18c
SELECT
      f.facility_id,
      f.facility_name,
      f.member_id,
      f.instructor_id,
      f.facility_phone,
      f.facility_content,
      f.facility_image_path,
      f.facility_person_max,
      f.facility_person_min,
      f.facility_use,
      f.facility_reg_date,
      f.facility_mod_date,
      TO_CHAR(f.facility_open_time,'HH24:MI') AS facility_open_time,
      TO_CHAR(f.facility_close_time,'HH24:MI') AS facility_close_time,
      f.facility_money,
      f.facility_type 
    FROM facility_tbl f
    WHERE
        f.facility_name LIKE '%' || '국민' || '%'
        AND f.facility_use = (CASE WHEN facility_use = 'true' THEN 'Y' ELSE 'N' END)
        AND f.facility_type = '수영장'
        ORDER BY f.facility_name ASC
--      ORDER BY f.facility_reg_date ASC
--	    ORDER BY f.facility_reg_date DESC
--      ORDER BY f.facility_id
-- 	    ORDER BY f.facility_id
      OFFSET 0 * 10 ROWS FETCH NEXT 10 ROWS ONLY;

-----------------------------------------------------------------------------

    
-- Oracle 11g

-- 기본 구문)

-- 페이징 적용
-- 페이지당 10개씩의 레코드 조회
-- rownum : 번호 출력
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             -- 구문 삽입 시작 : OFFSET 제외한 구문 !
             SELECT *
			 FROM users
             ORDER BY userid DESC
             -- 구문 삽입 끝
           ) m  
      )  
WHERE page = 1;      

----------------------------------------------------------------

SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT
			      f.facility_id,
			      f.facility_name,
			      f.member_id,
			      f.instructor_id,
			      f.facility_phone,
			      f.facility_content,
			      f.facility_image_path,
			      f.facility_person_max,
			      f.facility_person_min,
			      f.facility_use,
			      f.facility_reg_date,
			      f.facility_mod_date,
			      TO_CHAR(f.facility_open_time,'HH24:MI') AS facility_open_time,
			      TO_CHAR(f.facility_close_time,'HH24:MI') AS facility_close_time,
			      f.facility_money,
			      f.facility_type 
		    FROM facility_tbl f
		    WHERE
		        f.facility_name LIKE '%' || '국민' || '%'
		        -- 전제) facility_use <= 'true' 전제
		        AND f.facility_use = (CASE WHEN 'true' = 'true' THEN 'Y' ELSE 'N' END)
                -- AND f.facility_use = DECODE('true', 'true', 'Y', 'N')
		        AND f.facility_type = '수영장'
		        ORDER BY f.facility_name ASC
           ) m  
      )  
WHERE page = 1;     



      