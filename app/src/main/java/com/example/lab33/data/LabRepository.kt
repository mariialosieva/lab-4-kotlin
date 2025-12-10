package com.example.lab33.data

import kotlinx.coroutines.flow.Flow

class LabRepository(private val labDao: LabDao) {
    val subjects: Flow<List<Subject>> = labDao.getSubjects()
    val doneLabs: Flow<List<LabWork>> = labDao.getDoneLabs()

    fun getLabsForSubject(subjectId: Long): Flow<List<LabWork>> {
        return labDao.getLabsForSubject(subjectId)
    }

    suspend fun getSubjectById(id: Long): Subject? {
        return labDao.getSubjectById(id)
    }

    suspend fun updateLabStatus(id: Long, isDone: Boolean) {
        labDao.updateLabStatus(id, isDone)
    }

    suspend fun initDataIfNeeded() {
        if (labDao.getSubjectCount() == 0) {
            val subjects = listOf(
                Subject(name = "Мережева безпека"),
                Subject(name = "Розгортання інформаційно-комунікаційних систем"),
                Subject(name = "Економіка та підприємництво"),
                Subject(name = "Проектування мультисервісних інформаційно-комунікаційних систем")
            )
            val subjectIds = labDao.insertSubjects(subjects)

            val labs = mutableListOf<LabWork>()
            // Map IDs back to subjects if needed, or just iterate indices
            // We have 4 subjects, so subjectIds should have 4 elements.
            
            // Random constants as requested
            val LABS_FOR_NETWORK_SECURITY = (4..10).random()
            val LABS_FOR_ICT_DEPLOYMENT = (4..10).random()
            val LABS_FOR_ECONOMY = (4..10).random()
            val LABS_FOR_MULTISERVICE_DESIGN = (4..10).random()
            
            val counts = listOf(
                LABS_FOR_NETWORK_SECURITY,
                LABS_FOR_ICT_DEPLOYMENT,
                LABS_FOR_ECONOMY,
                LABS_FOR_MULTISERVICE_DESIGN
            )

            subjectIds.forEachIndexed { index, subjectId ->
                val count = counts.getOrElse(index) { 5 }
                for (i in 1..count) {
                    labs.add(
                        LabWork(
                            subjectId = subjectId,
                            title = "Лабораторна робота $i",
                            statusDone = false
                        )
                    )
                }
            }
            labDao.insertLabs(labs)
        }
    }
}
