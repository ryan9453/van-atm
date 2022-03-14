package com.example.atm


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.atm.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 因為 getSharePreferences為 Activity父類別 Context的方法
        // 在 Fragment類別裡不能直接使用，需要有 context認證
        // 故使用 requireContext()取得
        val pref =requireContext().getSharedPreferences("atm", Context.MODE_PRIVATE)
        val user = pref.getString("USER", "")
        if (user != "") {
            binding.edUsername.setText(user)
        }

        binding.cdRemember.setOnCheckedChangeListener { compoundButton, checked ->
            if (!checked) {
                pref.edit().putString("USER", "").apply()
            }
        }


        binding.buttonFirst.setOnClickListener {
            // login stuff
            val username = binding.edUsername.text.toString()
            val password = binding.edPassword.text.toString()
            if ( username == "jack" && password == "1234") {
                // save username to preferences
                // atm為要存在 XML裡的名字，後面是一個設定模式的整數
                // 現在通常用私人模式



                // 開始儲存資料到 pref
                // .commit()是下一行要執行，.apply()是有空就執行
                pref.edit()
                    .putString("USER", username)
                    .putInt("LEVEL", 3)
                    .apply() // .commit

                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            } else {
                // error
                // 錯誤對話框
                AlertDialog.Builder(requireContext())
                    .setTitle("Login")
                    .setMessage("Login Failed!")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}