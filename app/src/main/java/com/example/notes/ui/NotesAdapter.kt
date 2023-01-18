package com.example.notes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.NoteLayoutBinding
import com.example.notes.db.entities.Note

class NotesAdapter(
    private val notes: List<Note>
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.apply {
            noteTitle.text = notes[position].title
            noteDescription.text = notes[position].note
        }

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionAddNote()
            action.note = notes[position]

            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int = notes.size
}