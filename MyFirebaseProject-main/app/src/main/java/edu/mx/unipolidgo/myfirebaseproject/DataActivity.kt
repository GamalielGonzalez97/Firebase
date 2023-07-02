package edu.mx.unipolidgo.myfirebaseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.model.Document
import edu.mx.unipolidgo.myfirebaseproject.databinding.ActivityDataBinding

const val TAG = "FIRESTORE"

class DataActivity : AppCompatActivity() {

    private var binding: ActivityDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_data)

        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        uploadData()
        readData()

    }
    class FirebaseUtils {
        val firestoreDatabase = FirebaseFirestore.getInstance()
    }

    private fun uploadData(){
        binding!!.btnUploadData.setOnClickListener {
            val hashMap = hashMapOf<String, Any>(
                "Name" to "Pizza",
                "Description" to "Peperoni con doble queso",
                "Size" to "Grande",
                "Price" to "$240",
                "Image" to "https://firebasestorage.googleapis.com/v0/b/myfirebase-1279e.appspot.com/o/Platillos%2Fpepperoni.jpg?alt=media&token=625973b2-ccb9-4c73-8b99-fa144b77349b"
            )
            FirebaseUtils().firestoreDatabase.collection("platillos")
                .add(hashMap)
                .addOnSuccessListener {
                    Log.d(TAG, "Se añadió el documento con ID {$it.id}")
                    Toast.makeText(this, "Se ha creado un nuevo documento con ID {$it.id}", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{
                    Log.d(TAG, "El documento no se pudo agregar {$it}")
                }

        }
    }

    private fun readData(){
        binding!!.btnReadData.setOnClickListener {
            FirebaseUtils().firestoreDatabase.collection("platillos")
                .get()
                .addOnSuccessListener { QuerySnapshot ->
                    QuerySnapshot.forEach{ Document ->
                        Log.d(TAG, "Se lee el documento con el ID: {${Document.data}}")
                        Toast.makeText(this,"Los documentos se publican en la consola",
                        Toast.LENGTH_LONG).show()

                    }
                }
                .addOnFailureListener{
                    Log.w(TAG,"Los documentos no se pudieron recuperar {${it.message}}")
                    Toast.makeText(this, "Los documentos no se pudieron recuperar",
                    Toast.LENGTH_LONG).show()
                }
        }
    }




}
