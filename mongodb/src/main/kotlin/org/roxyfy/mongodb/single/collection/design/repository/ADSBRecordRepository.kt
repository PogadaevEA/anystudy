package org.roxyfy.mongodb.single.collection.design.repository

import org.roxyfy.mongodb.single.collection.design.model.ADSBRecord
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ADSBRecordRepository : MongoRepository<ADSBRecord, String> {
    @Query("{_class: \"ADSBRecord\"}")
    fun findAllADSBRecords(): List<ADSBRecord>

    @Query(value="{_id: /^?0/, _class: \"ADSBRecord\"}", sort = "{_id: 1}")
    fun findADSBDataByIcaoAddr(icaoAddr: String): List<ADSBRecord>

}
