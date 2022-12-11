package com.vfadin.events.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vfadin.events.ui.theme.BorderGray
import com.vfadin.events.ui.theme.PlaceholderGray
import com.vfadin.events.ui.theme.SurfaceGray
import com.vfadin.events.ui.theme.TextBlack

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    type: FieldType = FieldType.Outlined,
    onValueChange: (String) -> Unit = {},
    contentTextStyle: TextStyle = TextStyle(),
    placeHolderStyle: TextStyle = contentTextStyle.copy(color = PlaceholderGray),
    placeHolderString: String,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    cursorColor: Color = TextBlack,
    leadingIconEnabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    leadingClickable: (() -> Unit)? = null,
    trailingIconEnabled: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    trailingClickable: (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var isFocused by remember { mutableStateOf(false) }
    var placeHolderShown by remember { mutableStateOf(true) }
    var textColor by remember { mutableStateOf(TextBlack) }
    var borderColor by remember { mutableStateOf(BorderGray) }

    borderColor = if (isError) MaterialTheme.colors.error
    else if (!enabled || (type != FieldType.Outlined)) Color.Transparent
    else if (!isFocused) BorderGray
    else TextBlack

    textColor = if (isError) MaterialTheme.colors.error
    else if (contentTextStyle.color != Color.Unspecified) contentTextStyle.color
    else TextBlack

    val innerTextPaddingEndVal by remember {
        mutableStateOf(
            if (trailingIcon != null) TextBoxEndPaddingWithTrailing
            else TextBoxEndPadding
        )
    }
    BasicTextField(
        value = value,
        enabled = enabled,
        readOnly = readOnly,
        interactionSource = interactionSource,
        modifier = modifier
            .sizeIn(minHeight = FieldMinHeight)
            .clip(FieldShape)
            .background(if (enabled && (type != FieldType.Filled)) Color.Transparent else SurfaceGray)
            .onFocusChanged {
                isFocused = it.isFocused
                placeHolderShown = !it.isFocused
            }
            .border(
                width = if (type != FieldType.Outlined) 0.dp else FieldBorderThickness,
                color = borderColor,
                shape = FieldShape
            ),
        onValueChange = onValueChange,
        textStyle = contentTextStyle.copy(color = textColor),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = FieldStartPadding),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (leadingIcon != null && leadingIconEnabled) {
                            Box(
                                modifier = Modifier
                                    .padding(end = LeadingIconEndPadding)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(LeadingIconBoxSize)
                                        .conditional(enabled && (leadingClickable != null)) {
                                            clickable {
                                                leadingClickable?.let { it() }
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    leadingIcon()
                                }
                            }
                        }
                        if (value.isEmpty() && (placeHolderShown || readOnly)) {
                            Text(
                                text = placeHolderString,
                                style = placeHolderStyle,
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = innerTextPaddingEndVal)
                            .padding(vertical = TextVerticalPadding)
                    ) {
                        innerTextField()
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = TrailingIconEndPadding),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (trailingIcon != null && trailingIconEnabled) {
                        Box(
                            modifier = Modifier
                                .size(TrailingIconBoxSize)
                                .conditional(enabled && (trailingClickable != null)) {
                                    clickable {
                                        trailingClickable?.let { it() }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            trailingIcon()
                        }
                    }
                }
            }
        },
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(cursorColor),
        visualTransformation = visualTransformation,
    )
}

private val HorizontalGapBetweenElements = 10.dp

private val FieldMinHeight = 40.dp
private val FieldStartPadding = HorizontalGapBetweenElements
private val FieldBorderThickness = 1.dp
private val FieldShape = RoundedCornerShape(4.dp)

private val LeadingIconBoxSize = 24.dp
private val LeadingIconEndPadding = HorizontalGapBetweenElements

private val TrailingIconBoxSize = 24.dp
private val TrailingIconEndPadding = HorizontalGapBetweenElements

private val TextVerticalPadding = 11.dp
private val TextBoxEndPadding = 0.dp
private val TextBoxEndPaddingWithTrailing =
    HorizontalGapBetweenElements + TrailingIconBoxSize + TrailingIconEndPadding

private fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(this))
    } else {
        this
    }
}

enum class FieldType {
    Outlined,
    Filled,
    Transparent
}

