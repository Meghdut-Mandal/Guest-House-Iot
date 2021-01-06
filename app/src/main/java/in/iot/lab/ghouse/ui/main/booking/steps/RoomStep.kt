package `in`.iot.lab.ghouse.ui.main.booking.steps

import `in`.iot.lab.ghouse.R
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.chip.Chip
import ernestoyaquello.com.verticalstepperform.Step
import kotlinx.android.synthetic.main.step_room_layout.view.*

class RoomStep : Step<String>("Room ") {
    var currentRoomCode = ""
    val roomCodes = arrayListOf<String>()
    var chipGroup: com.google.android.material.chip.ChipGroup? = null


    override fun getStepData(): String = currentRoomCode

    override fun getStepDataAsHumanReadableString(): String {
        return "Room $currentRoomCode"
    }

    override fun restoreStepData(data: String) {
        currentRoomCode = data
    }

    override fun isStepDataValid(stepData: String): IsDataValid {
        if (stepData.isNotEmpty()) {
            return IsDataValid(true)
        }
        return IsDataValid(false, "Must Select a Room")
    }

    override fun createStepContentLayout(): View {
        val inflater = LayoutInflater.from(context)
        val roomLayout = inflater.inflate(R.layout.step_room_layout, null, false)
        chipGroup = roomLayout.roomChipGroup
        init()
        return roomLayout
    }

    private fun init() {
        chipGroup?.let { group ->
            group.removeAllViews()
            roomCodes.map { code ->
                val chip = Chip(context)
                chip.text = code
                chip.isCheckable=true
                chip.setOnCheckedChangeListener { compoundButton, isChecked ->
                    if (isChecked) {
                        currentRoomCode = code
                    }

                    markAsCompletedOrUncompleted(true)
                }
                chip
            }.forEach {
                group.addView(it)
            }
        }

    }

    fun setUpRooms(newRoom: List<String>) {
        roomCodes.removeAll { true }
        roomCodes.addAll(newRoom)
        init()
    }


    override fun onStepOpened(animated: Boolean) {

    }

    override fun onStepClosed(animated: Boolean) {

    }

    override fun onStepMarkedAsCompleted(animated: Boolean) {

    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {

    }
}