const sbmenu = document.getElementById('sbmenu')
const sidebar = document.getElementById('sidebar')
const overlay = document.getElementById('overlay')

let menuOpen = false

function openMenu() {
    menuOpen = true;
    overlay.style.display = 'block'
    sidebar.style.width='340px'
}

function closeMenu() {
    menuOpen = false;
    overlay.style.display = 'none'
    sidebar.style.width = '0px'
}

sbmenu.addEventListener('click',function(){
    if(!menuOpen){
        openMenu()
    }
})

overlay.addEventListener('click',function(){
    if(menuOpen){
        closeMenu()
    }
})
