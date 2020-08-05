package com.dartharrmi.resipi.ui.recipe_detail.adapter

import com.dartharrmi.resipi.BR
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.adapter.BaseRecyclerViewAdapter
import com.dartharrmi.resipi.domain.InstructionStep

class RecipeStepAdapter(private val steps: List<InstructionStep>): BaseRecyclerViewAdapter(steps) {

    override fun itemLayoutId() = R.layout.item_step

    override fun itemToBindId() = BR.stepsBinder

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(RecipeStepBinder(steps[position]))
    }
}

class RecipeStepBinder(private val currentStep: InstructionStep) {

    fun getStepNumber() = currentStep.number.toString()

    fun getStepDetail() = currentStep.step
}