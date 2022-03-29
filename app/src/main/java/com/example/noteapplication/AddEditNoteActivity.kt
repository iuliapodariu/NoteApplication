package com.example.noteapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdt: EditText
    lateinit var noteDescriptionEdt: EditText
    lateinit var addupdateBtn: Button
    lateinit var viewModal: NoteViewModal
    var noteID = -1;


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEdt = findViewById(R.id.idEdtNoteTitle)
        noteDescriptionEdt = findViewById(R.id.idEdtNoteDescription)
        addupdateBtn = findViewById(R.id.idBtnAddUpdate)
        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModal::class.java)


        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {

            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("", -1)
            addupdateBtn.setText("Update Note")
            noteTitleEdt.setText(noteTitle)
            noteDescriptionEdt.setText(noteDesc)

        }else{
            addupdateBtn.setText("Save Note")
        }
addupdateBtn.setOnClickListener {
    val noteTitle = noteTitleEdt.text.toString()
    val noteDescription = noteDescriptionEdt.text.toString()

    if(noteType.equals("Edit"))  {
        if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
            val sdf = SimpleDateFormat("dd, MM, yyyy - HH:mm")
            val currentDate:String = sdf.format(java.util.Date())
            val updateNote = Note(noteTitle, noteDescription, currentDate  )
            updateNote.id = noteID
            viewModal.updateNote(updateNote)
            Toast.makeText(this," Note updated...", Toast.LENGTH_LONG).show()
        }
    }else{
        if(noteTitle.isNotEmpty()  && noteDescription.isNotEmpty()){
            val sdf = SimpleDateFormat("dd, MM, yyyy - HH:mm")
            val currentDate:String = sdf.format(java.util.Date())
            viewModal.addNote(Note(noteTitle, noteDescription,currentDate))
            Toast.makeText(this, "Note added...", Toast.LENGTH_LONG).show()
        }
    }

    startActivity(Intent(applicationContext,MainActivity::class.java))
    this.finish()
}
    }
}