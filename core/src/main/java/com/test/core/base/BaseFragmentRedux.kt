package com.test.core.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.test.filimo.R
import utils.ToastyMode
import javax.inject.Inject

abstract class BaseFragmentRedux<DB : ViewDataBinding, STATE : State, ACTION : Action, VM : BaseViewModelRedux<STATE, ACTION>> :
    Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var _binding: DB? = null
    protected val binding: DB get() = _binding!!
    private var callback: OnBackPressedCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, getResId(this.javaClass.simpleName), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { renderViewState(it) }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEffect.collect { renderDefaultViewEffect(it) }
        }
        onFragmentCreated(view, savedInstanceState)
    }

    abstract val viewModel: VM

    /**
     * convention naming for fragment class and fragment layout
     * */
    @LayoutRes
    fun getResId(className: String): Int {
        var result = ""
        val words = className.split(
            regex = Regex(
                "(?<=[A-Z])(?=[A-Z][a-z])|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])"
            )
        )
        val orderedWords = words.reversed()
        orderedWords.forEachIndexed { index, s ->
            result += if (orderedWords.size - 1 != index) "${s.lowercase()}_"
            else s.lowercase()
        }
        return resources.getIdentifier(result, "layout", requireActivity().packageName)
    }

    abstract fun onFragmentCreated(view: View, savedInstanceState: Bundle?)

    abstract fun renderViewState(viewState: STATE)

    open fun renderCustomViewEffect(viewEffect: BaseEffect): Boolean = false

    private fun renderDefaultViewEffect(viewEffect: BaseEffect) {
        when (viewEffect) {
            is BaseEffect.ShowToast -> if (viewEffect.mode == ToastyMode.MODE_TOAST_DEFAULT) {
                Toast.makeText(requireContext(), viewEffect.message, Toast.LENGTH_SHORT).show()
            } else {
                toasty(viewEffect.message, viewEffect.mode)
            }
            else ->
                if (!renderCustomViewEffect(viewEffect))
                    throw Exception("RenderViewEffect Does Not Implemented")
        }
    }

    fun onFragmentBackPressed(owner: LifecycleOwner, call: () -> Unit) {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                call()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(owner, callback!!)
    }

    private fun toasty(title: String, @ToastyMode selectedMode: Int? = null) {
        val layout = layoutInflater.inflate(
            R.layout.layout_toast,
            requireView().findViewById(R.id.toast_root)
        )
        when (selectedMode) {

            ToastyMode.MODE_TOAST_SUCCESS -> {
                layout.findViewById<ImageView>(R.id.toast_img)
                    .setImageResource(R.drawable.ic_corroct_toast)
                layout.findViewById<ConstraintLayout>(R.id.toast_root)
                    .setBackgroundResource(R.drawable.bg_corroct_toast)
            }
            ToastyMode.MODE_TOAST_WARNING -> {
                layout.findViewById<ImageView>(R.id.toast_img)
                    .setImageResource(R.drawable.ic_warning_toast)
                layout.findViewById<ConstraintLayout>(R.id.toast_root)
                    .setBackgroundResource(R.drawable.bg_warning_toast)
                layout.findViewById<TextView>(R.id.toast_txt)
                    .setTextColor(resources.getColor(android.R.color.black))
            }
            ToastyMode.MODE_TOAST_ERROR -> {
                layout.findViewById<ImageView>(R.id.toast_img)
                    .setImageResource(R.drawable.ic_error_toast)
                layout.findViewById<ConstraintLayout>(R.id.toast_root)
                    .setBackgroundResource(R.drawable.bg_error_toast)
            }
            else -> {
                Toast.makeText(requireContext(), title, Toast.LENGTH_LONG).show()
            }

        }

        layout.findViewById<TextView>(R.id.toast_txt).text = title
        if (selectedMode != null) {
            Toast(requireActivity()).apply {
                setGravity(Gravity.BOTTOM, 0, 100)
                duration = Toast.LENGTH_LONG
                view = layout
            }.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (callback != null) {
            callback?.isEnabled = false
            callback?.remove()
        }
    }
}