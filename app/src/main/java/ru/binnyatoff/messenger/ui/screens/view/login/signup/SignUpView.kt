package ru.binnyatoff.messenger.ui.screens.view.login.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.binnyatoff.messenger.R
import ru.binnyatoff.messenger.ui.navigation.NavigationTree
import ru.binnyatoff.messenger.ui.components.DTextField
import ru.binnyatoff.messenger.ui.screens.view.login.signup.models.SignUpEvent
import ru.binnyatoff.messenger.ui.screens.view.login.signup.models.SignUpViewState
import ru.binnyatoff.messenger.ui.theme.AppTheme

@Composable
fun SignUpView(
    signUpViewModel: SignUpViewModel,
    navController: NavHostController
) {
    val viewState = signUpViewModel.viewState.observeAsState(SignUpViewState())
    with(viewState.value) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        ) {
            item {
                Text(
                    modifier = Modifier.padding(top = 30.dp),
                    text = stringResource(id = R.string.sign_up_title),
                    style = TextStyle(
                        color = AppTheme.colors.primaryTextColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                )
            }
            item {
                DTextField(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    value = username,
                    secureText = false,
                    onValueChanged = {
                        signUpViewModel.obtainEvent(SignUpEvent.UserNameChanged(it))
                    },
                    label = stringResource(id = R.string.user_name_label)
                )
            }

            item {
                DTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    value = login,
                    secureText = false,
                    onValueChanged = {
                        signUpViewModel.obtainEvent(SignUpEvent.LoginChanged(it))
                    },
                    label = stringResource(id = R.string.login_label)
                )
            }

            item { Spacer(modifier = Modifier.height(15.dp)) }


            item {
                DTextField(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .height(60.dp)
                        .fillMaxWidth(),
                    value = password,
                    secureText = true,
                    onValueChanged = {
                        signUpViewModel.obtainEvent(SignUpEvent.PasswordChanged(it))
                    },
                    label = stringResource(id = R.string.password_label)
                )
            }
            item {
                Row(
                    modifier = Modifier.padding(top = 40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMeChecked,
                        onCheckedChange = {
                            signUpViewModel.obtainEvent(
                                SignUpEvent.CheckBoxClicked(
                                    it
                                )
                            )
                        },
                        enabled = !isProgress,
                        colors = CheckboxDefaults.colors(
                            checkedColor = AppTheme.colors.primaryTintColor
                        )
                    )

                    Text(text = stringResource(id = R.string.remember_check_title))
                }
            }


            item { Spacer(modifier = Modifier.height(15.dp)) }

            item {
                Row() {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = AppTheme.colors.primaryTintColor),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .width(180.dp)
                            .height(60.dp),
                        onClick = {
                            signUpViewModel.obtainEvent(SignUpEvent.LoginButtonClicked)
                        }) {
                        if (!isProgress) {
                            Text(text = stringResource(id = R.string.sign_up_button))

                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = AppTheme.colors.primaryTintColor),
                        shape = RoundedCornerShape(10.dp),
                        enabled = !isProgress,
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .width(180.dp)
                            .height(60.dp),
                        onClick = {
                            navController.navigate(NavigationTree.SignIn.name) {
                                popUpTo(NavigationTree.SignUp.name){
                                    inclusive = true
                                }
                            }
                            signUpViewModel.obtainEvent(SignUpEvent.LoginButtonClicked)
                        }) {
                        Text(text = stringResource(id = R.string.sign_in_title))
                    }

                }
            }
        }
    }

}
