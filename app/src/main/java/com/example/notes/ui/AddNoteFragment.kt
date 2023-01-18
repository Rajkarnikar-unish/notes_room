package com.example.notes.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.Navigation
import com.example.notes.R
import com.example.notes.databinding.FragmentAddNoteBinding
import com.example.notes.db.NoteDatabase
import com.example.notes.db.entities.Note
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {

    private var note: Note? = null

    private lateinit var binding: FragmentAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun deleteNote() {
        if (activity != null) {
            AlertDialog.Builder(context).apply {
                setTitle("Are you sure?")
                setMessage("You cannot undo this action.")
                setPositiveButton("Delete") {_, _  ->
                    launch {
                        NoteDatabase(context).getNoteDao().deleteNote(note!!)

                        val action = AddNoteFragmentDirections.actionSaveNote()
                        Navigation.findNavController(binding.root).navigate(action)
                    }
                }
                setNegativeButton("Cancel") {_, _  -> }
            }.create().show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menu.clear()
                if(menu.size()==0) menuInflater.inflate(R.menu.delete_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.delete -> if(note != null) deleteNote() else context?.toast("Unable to delete the note.")
                }
                return false
            }
        })


        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note

            binding.titleEditText.setText(note?.title)
            binding.noteEditText.setText(note?.note )
        }

        binding.btnSave.setOnClickListener {view->
            val noteTitle = binding.titleEditText.text.toString().trim()
            val noteBody = binding.noteEditText.text.toString().trim()

            if (noteTitle.isEmpty()) {
                binding.titleEditText.error = "Title required"
                binding.titleEditText.requestFocus()
                return@setOnClickListener
            }

            if (noteBody.isEmpty()) {
                binding.noteEditText.error = "Title required"
                binding.noteEditText.requestFocus()
                return@setOnClickListener
            }

            launch {

                context?.let {
                    val mNote = Note(noteTitle, noteBody)

                    if(note == null) {
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")
                    } else {
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note Updated")

                    }

                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}