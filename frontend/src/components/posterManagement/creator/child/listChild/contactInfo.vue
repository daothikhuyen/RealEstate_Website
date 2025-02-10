
<template>
    <div class="contact">
        <div class="input_special d-flex justify-content-between align-items-center">
            <input type="text" class="input_item" placeholder="attribute.placeholder">
        </div>
        <div class="input_special d-flex justify-content-between align-items-center">
            <input type="text" class="input_item" placeholder="attribute.placeholder">
        </div>
        <div class="input_special d-flex justify-content-between align-items-center">
            <input type="text" class="input_item" placeholder="attribute.placeholder">
        </div>
    </div>

</template>

<script lang="ts">
import { ref,defineComponent } from 'vue'
import extensionApi from '../../../../../Api/extentionApi'

export default defineComponent({
    name : 'Extension',
    components: {

    },
})
</script>

<style scoped>

</style>

