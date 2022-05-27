package com.example.challenge6.view

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.challenge6.R
import com.example.challenge6.manager.UserManager
import com.example.challenge6.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewModel: ViewModelUser
    lateinit var userManager : UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireContext())
        userManager.userNAME.asLiveData().observe(requireActivity()) {
            updateUsername.setText(it)
            userManager.userALAMAT.asLiveData().observe(requireActivity()) {
                updateAlamat.setText(it)
                userManager.userCOMPLETE.asLiveData().observe(requireActivity()) {
                    updateLengkap.setText(it)
                    userManager.userTANGGAL.asLiveData().observe(requireActivity()) {
                        updateTanggal.setText(it)
                        userManager.userIMAGE.asLiveData().observe(requireActivity()) {
                            Glide.with(requireActivity()).load(it).into(imageBtn)
                            userManager.userID.asLiveData().observe(requireActivity()){
                                txtProfile.hint = it
                            }



                        }
                    }

                }
            }
        }
        btnPick.setOnClickListener {
            if (askForPermissions()) {
                setImage()
            }
        }
        btnLogout.setOnClickListener {
            val a = AlertDialog.Builder(requireContext())
                .setTitle("Log Out")
                .setMessage("Apakah yakin Log out dari aplikasi?")
                .setPositiveButton("YA") { dialog: DialogInterface, i: Int ->
                    logout()
                }
                .setNegativeButton("TIDAK") { dialog : DialogInterface, i : Int ->
                    dialog.dismiss()
                }
                .show()


        }

        btnUpdate.setOnClickListener {
            val username1 = updateUsername.text.toString()
            val namalengkap1 = updateLengkap.text.toString()
            val alamat1 = updateAlamat.text.toString()
            val tanggal1 = updateTanggal.text.toString()
            val id2 = txtProfile.hint.toString().toInt()
            update(id2, username1, namalengkap1, alamat1, tanggal1)



        }

    }
    fun update(id : Int, username : String, completeName :String, dateofbirth : String, address : String){
        viewModel = ViewModelProvider(requireActivity()).get(ViewModelUser::class.java)
        viewModel.getUserLiveDataObserver().observe(requireActivity()) {
            if (it != null){
                GlobalScope.launch {
                    userManager.update(username,address,completeName,dateofbirth)
                }
                Toast.makeText(requireContext(), "Berhasil update data", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.updateLiveData(id, username, completeName, dateofbirth, address)
    }
    private fun setImage() {
        val cameraIntent = Intent(Intent.ACTION_GET_CONTENT)
        cameraIntent.type = "image/*"
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(cameraIntent, 2000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2000 && data != null){
            imageBtn.setImageURI(data?.data)
            GlobalScope.launch {
                userManager.setImage(data?.data.toString())
            }
        }else {

        }
    }
    fun logout(){
        GlobalScope.launch {
            userManager.logout()
            userManager.setStatus("no")
            userManager.deleteImage()
        }
        view?.findNavController()?.navigate(R.id.action_profileFragment_to_loginFragment)


    }

    fun isPermissionsAllowed(): Boolean {
        return if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }

    fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(requireActivity(),arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),2000)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        when (requestCode) {
            2000 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted, you can perform your operation here
                } else {
                    // permission is denied, you can ask for permission again, if you want
                    //  askForPermissions()
                }
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", "com.binar.challengechapterenam", null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel",null)
            .show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}