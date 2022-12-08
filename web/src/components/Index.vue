<template>
    <div>
        <p>Just a redirect getting the access token</p>
    </div>
</template>
<script>
import contactsService from '@/services/contacts';

export default {
  mounted() {
    const accessToken = contactsService.obtainAccessToken();
    // const accessToken = this.$route.query.accessToken;
    contactsService.importContacts(accessToken);
  },
};
</script>
