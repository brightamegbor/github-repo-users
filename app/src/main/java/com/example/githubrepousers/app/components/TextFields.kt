package com.example.githubrepousers.app.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.example.githubrepousers.ui.theme.*


@Composable
fun PrimaryTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Done,
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int? = null,
    colors: TextFieldColors? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = { label?.let { Text(text = it) } },
        placeholder = { Text(text = placeholder ?: "") },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines ?: 1,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(DefaultBorderRadiusMedium),
        colors = colors ?: TextFieldDefaults.textFieldColors(
            backgroundColor = colorWhite,
            placeholderColor = if (isError) ColorRed else ColorLightGrey,
            textColor = colorBlack,
            focusedIndicatorColor = if (isError) ColorRed else ColorLightGrey2,
            unfocusedIndicatorColor = if (isError) ColorRed else ColorLightGrey2,
            focusedLabelColor = ColorPrimary,
        )
    )
}