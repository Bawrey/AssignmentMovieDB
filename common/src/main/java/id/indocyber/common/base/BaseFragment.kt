package id.indocyber.common.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<Binding : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    abstract val viewModel: VM
    abstract val layoutResourceId: Int
    lateinit var binding: Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = this
        bindingExt(binding)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigationEvent.observe(this) {
            findNavController().navigate(it)
        }
        viewModel.popBackStackEvent.observe(this) {
            findNavController().popBackStack()
        }
    }

    open fun bindingExt(binding: Binding) {}

    open fun alertDialogBuilder(title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.cancel()
            }
            .create().show()
    }
}