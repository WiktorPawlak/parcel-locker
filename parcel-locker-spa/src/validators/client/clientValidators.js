export function validatePassword(password, repeatedPassword) {
  // const re = new RegExp(
  //   '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})'
  // );
  return password !== '' && password === repeatedPassword;
}

export function validateEmail(email) {
  const re = new RegExp(
    '^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@' +
      '[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$'
  );

  return re.test(email);
}

export function validateUsername(uesrname) {
  return (
    uesrname !== '' &&
    uesrname.length >= 3 &&
    uesrname.length <= 40 &&
    !uesrname.includes(' ')
  );
}
