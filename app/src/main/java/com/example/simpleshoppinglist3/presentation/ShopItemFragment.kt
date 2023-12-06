package com.example.simpleshoppinglist3.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpleshoppinglist3.R
import com.example.simpleshoppinglist3.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment() : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        Log.d("ShopItemFragment", "onAttach")
        Log.d("ShopItemFragment", "===================================")
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ShopItemFragment", "onCreate")
        Log.d("ShopItemFragment", "===================================")
        super.onCreate(savedInstanceState)
        parseParams()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ShopItemFragment", "onCreateView")
        Log.d("ShopItemFragment", "===================================")
        return layoutInflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("ShopItemFragment", "onViewCreated")
        Log.d("ShopItemFragment", "===================================")
        super.onCreate(savedInstanceState)
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        addChangeTextListeners()
        launchRightScreenMode()
        observeViewModel()
    }

    //////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    override fun onStart() {
        Log.d("ShopItemFragment", "onStart")
        Log.d("ShopItemFragment", "===================================")
        super.onStart()
    }

    override fun onResume() {
        Log.d("ShopItemFragment", "onResume")
        Log.d("ShopItemFragment", "===================================")
        super.onResume()
    }

    override fun onPause() {
        Log.d("ShopItemFragment", "onPause")
        Log.d("ShopItemFragment", "===================================")
        super.onPause()
    }

    override fun onStop() {
        Log.d("ShopItemFragment", "onStop")
        Log.d("ShopItemFragment", "===================================")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("ShopItemFragment", "onDestroyView")
        Log.d("ShopItemFragment", "===================================")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("ShopItemFragment", "onDestroy")
        Log.d("ShopItemFragment", "===================================")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("ShopItemFragment", "onDetach")
        Log.d("ShopItemFragment", "===================================")
        super.onDetach()
    }


    /////////////////////////////////////////////////////////////////////////////////////


    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }

        viewModel.errorInputCountCheck.observe(viewLifecycleOwner) {
            etCount.setText(it)
        }
    }

    private fun launchRightScreenMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addChangeTextListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(inputCountCheck: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()

                viewModel.validateInputCountSymbols(inputCountCheck.toString())
                etCount.setSelection(etCount.getText().count());



//// Проверка корректности ввода 0. Автоматически ставит точку после нуля
//
//
//                var oldInput = etCount?.text.toString()
//                var newInput = ""
//
//                if (oldInput.count() > 1) {
//                    if (oldInput[0] == '0' && oldInput[1] != '.' && oldInput[1] != '.') {
//                        newInput = etCount?.text?.get(1)?.toString().toString()
//                        oldInput = "0.${newInput}"
//                        Log.d("MyLog111", oldInput)
//                        etCount.setText(oldInput)
//                        etCount.setSelection(etCount.getText().count());
//                    }
//                }
//
//// Проверка корректности ввода точки. Убирает первый символ если это точка.
//
//                if (oldInput.count() == 1) {
//                    if (oldInput[0] == '.' || oldInput[0] == '.') {
//                        oldInput = ""
//                        etCount.setText(oldInput)
//                    }
//                }
//
//// Проверка коректности ввода третьего символа. Не допускает установку 0.0
//
//                if (oldInput.count() == 3) {
//                    if (oldInput[0] == '0' && oldInput[1] == '.' && oldInput[2] == '0') {
//                        oldInput = "0."
//                        etCount.setText(oldInput)
//                        etCount.setSelection(etCount.getText().count());
//                    }
//                }
//// Проверка корректности ввода третьего символа. Не допускаеть установку третьего символа точку
//                if (oldInput.count() == 3) {
//                    if (oldInput[2] == '.' || oldInput[2] == '.') {
//                        val oldInputIndexOne = etCount?.text?.get(0)?.toString().toString()
//                        val oldInputIndexTwo = etCount?.text?.get(1)?.toString().toString()
//                        oldInput = "$oldInputIndexOne$oldInputIndexTwo"
//                        etCount.setText(oldInput)
//                        etCount.setSelection(etCount.getText().count());
//                    }
//                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is not absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()

    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_EDIT)
            intent.putExtra(SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}

