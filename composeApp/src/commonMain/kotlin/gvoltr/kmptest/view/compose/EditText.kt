package gvoltr.kmptest.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gvoltr.kmptest.view.compose.theme.AppColor.BackgroundActive
import gvoltr.kmptest.view.compose.theme.AppColor.Danger
import gvoltr.kmptest.view.compose.theme.AppColor.EditTextBorderActive
import gvoltr.kmptest.view.compose.theme.AppColor.EditTextBorderInactive
import gvoltr.kmptest.view.compose.theme.AppColor.Secondary

@Composable
fun EditTextFilled(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    placeholder: String,
    error: String? = null,
    maxLines: Int = 1,
    endPadding: Dp = 0.dp,
    imeAction: ImeAction,
    onImeActions: KeyboardActions = KeyboardActions(),
    keyboardType: KeyboardType = KeyboardType.Text,
    focusRequester: FocusRequester = rememberFocusRequester(),
    onTextChanged: (String) -> Unit
) {
    val activeBackground = BackgroundActive
    val inactiveBackground = EditTextBorderInactive
    val activeBorder = EditTextBorderActive
    val inactiveBorder = EditTextBorderInactive

    val background = rememberMutableStateOf(value = inactiveBackground)
    val backgroundBorder = rememberMutableStateOf(value = inactiveBorder)

    // localText stores latest local state produced produced by onValueChanged
    var localText by rememberMutableStateOf(value = TextFieldValue(text))

    if (text.length - localText.text.length > 1) {
        // if we paste text we need to move cursor to the end of line
        localText = TextFieldValue(
            text = text,
            selection = TextRange(text.length)
        )
    }
    OneTime(text) {
        if (text.isEmpty()) {
            // if text is empty we need to reset localText
            localText = TextFieldValue(text = "")
        }
    }

    Row (
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = backgroundBorder.value,
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = background.value, shape = RoundedCornerShape(12.dp))
            .padding(end = endPadding)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    background.value =
                        if (focusState.isFocused) activeBackground else inactiveBackground
                    backgroundBorder.value =
                        if (focusState.isFocused) activeBorder else inactiveBorder

                    // on focus move cursor to the end of line
                    // - if we just request focus through focusRequester it will move cursor to end of line
                    // - if we tap on text focus will be handled by TextField itself and
                    // we'll get new TextFieldValue from onValueChanged() and this value will be overridden
                    if (focusState.isFocused) {
                        localText = TextFieldValue(
                            text = text,
                            selection = TextRange(text.length)
                        )
                    }
                }
                .focusRequester(focusRequester),
            shape = RoundedCornerShape(12.dp),
            value = localText,
            label = if (error.isNullOrEmpty() && hint.isEmpty()) {
                null
            } else {
                {
                    Text(
                        text = if (error.isNullOrEmpty()) hint else error,
                        color = if (error.isNullOrEmpty()) Secondary else Danger
                    )
                }
            },
            placeholder = if (placeholder.isEmpty()) {
                null
            } else {
                {
                    Text(
                        text = placeholder,
                        color = Secondary
                    )
                }
            },
            isError = !error.isNullOrEmpty(),
            onValueChange = {
                if (it.text.length - localText.text.length > 1) {
                    // if we paste text we need to move cursor to the end of line
                    localText = it.copy(selection = TextRange(it.text.length))
                } else {
                    localText = it
                }
                onTextChanged(it.text)
            },
            singleLine = maxLines == 1,
            maxLines = maxLines,
            colors = textFieldColors(
                backgroundColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
            keyboardActions = onImeActions
        )
    }
}

@Composable
private fun textFieldColors(backgroundColor: Color) = TextFieldDefaults.textFieldColors(
    backgroundColor = backgroundColor,
    focusedLabelColor = Secondary,
    unfocusedLabelColor = Secondary,
    disabledLabelColor = Secondary,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
)
