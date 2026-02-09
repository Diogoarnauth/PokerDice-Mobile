@OptIn(ExperimentalMaterial3Api::class)
@Composable

const val TITLE_VIEW_TAG = "TitleView"

fun TitleView(
    creators: List<String>,
    startMatch: ()-> Unit,
    profile: () -> Unit,
    about: () -> Unit

) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize() 
            .testTag(TITLE_VIEW_TAG),
        topBar = {
            GenericTopAppBar(
                title = stringResource(R.string.app_name),
                actions = {
                    TextButton(
                        onClick = { loginFunction() },
                        modifier = Modifier.testTag(HOMEPAGE_LOGIN_BUTTON)
                    ) {
                        Text(
                            text = stringResource(R.string.login),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    TextButton(
                        onClick = { signUpFunction() },
                        modifier = Modifier.testTag(HOMEPAGE_SIGNUP_BUTTON)
                    ) {
                        Text(
                            text = stringResource(R.string.sign_Up),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.welcome),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.testTag(HOMEPAGE_MADE_BY_TEXT),
                    text = stringResource(R.string.creators),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                creators.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}
