package com.erenyurtcu.nutritionproject.adapter

import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.erenyurtcu.nutritionproject.databinding.FragmentNutritionListBinding
import com.erenyurtcu.nutritionproject.databinding.NutritionRecyclerRowBinding
import com.erenyurtcu.nutritionproject.model.Nutrition
import com.erenyurtcu.nutritionproject.util.doPlaceHolder
import com.erenyurtcu.nutritionproject.util.downloadImage
import com.erenyurtcu.nutritionproject.view.NutritionListFragmentDirections
import java.net.BindException

class NutritionRecyclerAdapter (val nutritionList : ArrayList<Nutrition>) : RecyclerView.Adapter<NutritionRecyclerAdapter.NutritionViewHolder>() {
    class NutritionViewHolder(val binding: NutritionRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionViewHolder {
        val binding = NutritionRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NutritionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return nutritionList.size
    }

    fun updateNutritionList(newNutritionList : List<Nutrition>){
        nutritionList.clear()
        nutritionList.addAll(newNutritionList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NutritionViewHolder, position: Int) {
        val nutrition = nutritionList[position]
        holder.binding.name.text = nutrition.nutritionName
        holder.binding.calorie.text = nutrition.nutritionCalorie

        holder.itemView.setOnClickListener{
            val action = NutritionListFragmentDirections.actionNutritionListFragmentToNutritionDetailFragment(nutrition.uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageView.downloadImage(nutrition.nutritionImage, doPlaceHolder(holder.itemView.context))
    }
}