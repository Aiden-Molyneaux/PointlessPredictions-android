package carleton.comp2601.pointlesspredictions.ui.s2_home.viewmodels

import carleton.comp2601.pointlesspredictions.entities.Prediction

data class HomeState (
    val user_id: Int? = null,
    val username: String? = null,
    val password: String? = null,
    val predictionText: String? = null,
    val day: String? = null,
    val month: String? = null,
    val year: String? = null,
    val isChecked: Boolean = true,
    val error: String? = null,
    val predictionList: List<Prediction> = emptyList(),
    val numberOfTrue: Int? = 0,
    val numberOfFalse: Int? = 0,
    val numberOfUnconfirmed: Int? = 0
)