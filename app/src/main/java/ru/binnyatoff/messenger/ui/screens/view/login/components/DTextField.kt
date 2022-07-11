package ru.binnyatoff.messenger.ui.screens.view.login.components


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.binnyatoff.messenger.ui.theme.AppTheme

@Composable
fun DTextField(
    value: String,
    modifier: Modifier = Modifier,
    label: String,
    secureText: Boolean,
    enabled: Boolean = true,
    onValueChanged: (String) -> Unit
) {
    TextField(
        modifier = modifier,
        value = value,
        label = { Text(text = label) },
        onValueChange = onValueChanged,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = AppTheme.colors.secondaryBackground,
            textColor = AppTheme.colors.primaryTextColor,
            disabledIndicatorColor = AppTheme.colors.primaryBackground,
            errorIndicatorColor = AppTheme.colors.primaryBackground,
            focusedIndicatorColor = AppTheme.colors.primaryBackground,
            unfocusedIndicatorColor = AppTheme.colors.primaryBackground
        ),
        visualTransformation = if (secureText) PasswordVisualTransformation() else
            VisualTransformation.None,
    )
}