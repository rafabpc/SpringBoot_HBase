package com.documentanalyzer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.documentanalyzer.model.Upload;

public interface UploadRepository extends CrudRepository<Upload, Long> {

    @Query("from Upload u join u.user.teams t where t.id=:team")
    public Iterable<Upload> findByTeam(Long team);

}
