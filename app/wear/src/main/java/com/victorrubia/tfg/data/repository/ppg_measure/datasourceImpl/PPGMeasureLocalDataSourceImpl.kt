package com.victorrubia.tfg.data.repository.ppg_measure.datasourceImpl

import com.victorrubia.tfg.data.db.PPGMeasureDao
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the [PPGMeasureLocalDataSource] interface for retrieving data from the local database.
 * (i.e. Room database)
 *
 * @property ppgMeasureDao The DAO for the [PPGMeasure] table.
 */
class PPGMeasureLocalDataSourceImpl(
    private val ppgMeasureDao: PPGMeasureDao
) : PPGMeasureLocalDataSource {

    /**
     * {@inheritDoc}
     */
    override suspend fun getPPGMeasureFromDB(): List<PPGMeasure> {
        return ppgMeasureDao.getPPGMeasures()
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun addPPGMeasureToDB(ppgMeasures: PPGMeasure) {
        CoroutineScope(Dispatchers.IO).launch{
            ppgMeasureDao.savePPGMeasure(ppgMeasures)
        }
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            ppgMeasureDao.deletePPGMeasures()
        }
    }
}