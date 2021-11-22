package hu.bme.aut.movesy.ui.userpackages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

import hu.bme.aut.movesy.databinding.ProfileTransporterDataBinding
import hu.bme.aut.movesy.databinding.ProfileUserDataBinding
import hu.bme.aut.movesy.viewmodel.Status
import hu.bme.aut.movesy.viewmodel.UserUtils
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {


    private lateinit var binding: ProfileTransporterDataBinding
    @Inject
    lateinit var userUtils: UserUtils
    private val viewModel: ProfileViewModel by viewModels()

    lateinit var prop: ProfileUserDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileTransporterDataBinding.inflate(inflater, container, false)

        prop = binding.transporterEditData

        prop.etProfilUsername.hint = userUtils.getUser()?.username ?: ""
        prop.etProfileEmail.hint = userUtils.getUser()?.email ?: ""
        prop.etProfilePhone.hint = userUtils.getUser()?.telephone ?: ""

        setName()
        setEmail()
        setPhone()

        setData()

        // kotlin == is equal to the .equals
        if(userUtils.getUser()!!.role == "TRANSPORTER"){
            binding.ProfileTransporterPackageProperties.root.visibility = View.VISIBLE
            binding.btnMyRatings.visibility = View.VISIBLE
            binding.tvTransportParameters.visibility = View.VISIBLE
        }

        binding.ProfileTransporterPackageProperties.radioGroup.setOnCheckedChangeListener{_,_->
            val user = userUtils.getUser()!!
            when(id){
                binding.ProfileTransporterPackageProperties.rdbtnSmall.id -> viewModel.saveUser(user.copy(size="SMALL"))
                binding.ProfileTransporterPackageProperties.rdbtnMedium.id -> viewModel.saveUser(user.copy(size="MEDIUM"))
                binding.ProfileTransporterPackageProperties.rdbtnBig.id -> viewModel.saveUser(user.copy(size="BIG"))
                binding.ProfileTransporterPackageProperties.rdbtnHuge.id -> viewModel.saveUser(user.copy(size="HUGE"))
                else->{}
            }
        }


        return binding.root
    }

    fun setName(){
        prop.ibName.setOnClickListener{
            setVisibilityEdit(
                prop.ibName,
                prop.ibSaveName,
                prop.ibDropChangesName,
                prop.etProfilUsername
            )
        }

        prop.ibSaveName.setOnClickListener{
            saveNewData()
            setVisibilityShow(
                prop.ibName,
                prop.ibSaveName,
                prop.ibDropChangesName,
                prop.etProfilUsername,
            )
        }


        prop.ibDropChangesName.setOnClickListener{
            setVisibilityShow(
                prop.ibName,
                prop.ibSaveName,
                prop.ibDropChangesName,
                prop.etProfilUsername,
            )
        }
    }

    fun setEmail(){
        prop.ibEmail.setOnClickListener{
            setVisibilityEdit(
                prop.ibEmail,
                prop.ibSaveEmail,
                prop.ibDropChangesEmail,
                prop.etProfileEmail
            )
        }

        prop.ibSaveEmail.setOnClickListener{
            saveNewData()
            setVisibilityShow(
                prop.ibEmail,
                prop.ibSaveEmail,
                prop.ibDropChangesEmail,
                prop.etProfileEmail,
            )
        }


        prop.ibDropChangesEmail.setOnClickListener{
            setVisibilityShow(
                prop.ibEmail,
                prop.ibSaveEmail,
                prop.ibDropChangesEmail,
                prop.etProfileEmail,
            )
        }

    }

    fun setPhone(){
        prop.ibPhone.setOnClickListener{
            setVisibilityEdit(
                prop.ibPhone,
                prop.ibSavePhone,
                prop.ibDropChangesPhone,
                prop.etProfilePhone
            )
        }

        prop.ibSavePhone.setOnClickListener{
            saveNewData()
            setVisibilityShow(
                prop.ibPhone,
                prop.ibSavePhone,
                prop.ibDropChangesPhone,
                prop.etProfilePhone,
            )
        }


        prop.ibDropChangesPhone.setOnClickListener{
            setVisibilityShow(
                prop.ibPhone,
                prop.ibSavePhone,
                prop.ibDropChangesPhone,
                prop.etProfilePhone,
            )
        }

    }




    fun setVisibilityEdit(edit: ImageButton, save: ImageButton, drop: ImageButton, et:EditText){
        edit.visibility = View.GONE
        save.visibility = View.VISIBLE
        drop.visibility = View.VISIBLE
        et.isEnabled = true
    }

    fun setVisibilityShow(edit: ImageButton, save: ImageButton, drop: ImageButton, et:EditText){
        edit.visibility = View.VISIBLE
        save.visibility = View.GONE
        drop.visibility = View.GONE
        et.isEnabled = false
    }


    fun saveNewData(){
        val user = userUtils.getUser()!!
        user.username = binding.transporterEditData.etProfilUsername.text.toString()
        user.email = binding.transporterEditData.etProfileEmail.text.toString()
        user.telephone = binding.transporterEditData.etProfilePhone.text.toString()

        viewModel.saveUser(user).observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS->{
                    TODO("set userUtils to the refreshed user")
                }
                Status.LOADING ->{}
                Status.ERROR ->{}
            }
        }
    }

    fun setData(){
        val user = userUtils.getUser() ?: return
        prop.etProfilUsername.setText(user.username)
        prop.etProfileEmail.setText(user.email)
        prop.etProfilePhone.setText(user.telephone)
    }

}