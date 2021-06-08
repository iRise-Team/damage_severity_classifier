package com.irise.damagedetection.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.irise.damagedetection.LoginActivity
import com.irise.damagedetection.databinding.FragmentProfileBinding
import com.irise.damagedetection.util.Util.TAG

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private var dbReference: DatabaseReference? = null
    private var db: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        dbReference = db?.reference!!.child("profile")

        showProfile()
    }

    private fun showProfile() {
        val user = auth.currentUser
        val reference = dbReference?.child(user?.uid!!)

        with(binding){
            tvEmail.text = user?.email

            reference?.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    tvName.text = snapshot.child("name").value.toString()
                    tvUsername.text = snapshot.child("username").value.toString()
                }

                override fun onCancelled(error: DatabaseError){
                    Toast.makeText(requireContext(), "Database Error", Toast.LENGTH_LONG).show()
                }
            })
            btnLogout.setOnClickListener{
                auth.signOut()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
            btnExit.setOnClickListener{
                activity?.finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}