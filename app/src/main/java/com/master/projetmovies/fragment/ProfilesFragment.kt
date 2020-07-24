package com.master.projetmovies.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.master.projetmovies.HomeActivity
import com.master.projetmovies.R
import com.master.projetmovies.databinding.FragmentProfilesBinding

class ProfilesFragment : Fragment() {
    private lateinit var binding: FragmentProfilesBinding
    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profiles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfilesBinding.bind(view)
        init()
        binding.signOut.setOnClickListener { logout() }
    }
    private fun init(){
        auth = Firebase.auth
        val currentUser =auth.currentUser
        currentUser?.also{
            binding.profileName.text = it.displayName
            binding.profileEmail.text = it.email
            Glide.with(this)
                .load(it.photoUrl)
                .placeholder(R.drawable.ic_baseline_person_24)
                .circleCrop()
                .into(binding.profileImage)
        }
    }
    private fun logout() {

        auth.signOut()
        val intent = Intent(context, HomeActivity::class.java)
        startActivity(intent)

    }
}
