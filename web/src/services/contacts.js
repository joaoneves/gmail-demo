import http from '@/utils/http';

const path = '/contacts';

const obtainAccessToken = () => {
  const oauthClientId = process.env.OAUTH_CLIENT_ID;
  const oauthClientSecret = process.env.OAUTH_CLIENT_SECRET;
  const callbackRedirectUri = process.env.CALLBACK_REDIRECT_URI;
  const oauthScope = process.env.OAUTH_SCOPE;

  const config = {
    headers: {
      Host: 'accounts.google.com',
      'Content-Type': 'application/x-www-form-urlencoded',
    },
  };

  const payloadRequest = {
    client_id: oauthClientId,
    client_secret: oauthClientSecret,
    redirect_uri: callbackRedirectUri,
    scope: oauthScope,
  };

  const { data } = http.post('https://accounts.google.com/o/oauth2/auth', payloadRequest, config);
  return data.accessToken;
};

const importContacts = (accessToken) => http.post(path, {
  accessToken,
});

export default {
  obtainAccessToken,
  importContacts,
};
