import Swal from "sweetalert2";

const showNotification = ((icon ,title,text,timer) => {
    return Swal.fire({
        icon: icon,
        title: title,
        showConfirmButton: false,
        timer: timer
    })
})

export default {
    showNotification
}