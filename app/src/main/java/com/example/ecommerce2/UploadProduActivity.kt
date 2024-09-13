package com.example.ecommerce2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UploadProduActivity : AppCompatActivity() {
    private lateinit var imgPreview: ImageView
    private lateinit var edtProdName: EditText
    private lateinit var edtProdPrice: EditText
    private lateinit var edtProdDesc: EditText
    private lateinit var btnUploadImg: Button
    private lateinit var btnUploadProd: Button
    private lateinit var progressBar: ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_produ)

        imgPreview = findViewById(R.id.img_preview)
        edtProdName = findViewById(R.id.prod_name_edt)
        edtProdPrice = findViewById(R.id.prod_price_edt)
        edtProdDesc = findViewById(R.id.prod_desc_edt)
        btnUploadImg = findViewById(R.id.btn_upload_img)
        btnUploadProd = findViewById(R.id.btn_upload_prod)
        progressBar = findViewById(R.id.progress_bar)



        btnUploadImg.setOnClickListener{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 101)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == RESULT_OK){
            val uri = data!!.data
            imgPreview.setImageURI(uri)

            btnUploadProd.setOnClickListener{
                progressBar.visibility = View.VISIBLE
                val prodName = edtProdName.text.toString()
                val prodPrice = edtProdPrice.text.toString()
                val prodDesc = edtProdDesc.text.toString()

                val fileName = UUID.randomUUID().toString()+".jpg"
                val storageReg = FirebaseStorage.getInstance().reference.child("productImages/$fileName")
                storageReg.putFile(uri!!).addOnSuccessListener {
                    val result = it.metadata?.reference?.downloadUrl
                    result?.addOnSuccessListener {
                        uploadProduct(prodName, prodPrice, prodDesc, it.toString())
                    }
                }
            }
        }
    }

    private fun uploadProduct(prodName: String, prodPrice: String, prodDesc: String, link: String) {
        Firebase.database.getReference("Products").child(prodName).setValue(
            Product(
                productName = prodName,
                productPrice = prodPrice,
                productDesc = prodDesc,
                productImg = link
            )
        ).addOnSuccessListener {
            progressBar.visibility = View.GONE
            Toast.makeText(this,"Product uploaded successfully", Toast.LENGTH_SHORT).show()
        }
    }
}