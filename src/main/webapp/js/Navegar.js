document.addEventListener('DOMContentLoaded', function () {
    const dropdowns = document.querySelectorAll('.dropdown');

    dropdowns.forEach(dropdown => {
        const dropbtn = dropdown.querySelector('.dropbtn');
        const dropdownContent = dropdown.querySelector('.dropdown-content');

        dropbtn.addEventListener('click', function (e) {
            e.stopPropagation();

            dropdowns.forEach(d => {
                if (d !== dropdown) {
                    d.querySelector('.dropdown-content').classList.remove('show');
                    d.querySelector('.dropbtn').setAttribute('aria-expanded', 'false');
                }
            });

            const isOpen = dropdownContent.classList.toggle('show');
            dropbtn.setAttribute('aria-expanded', isOpen ? 'true' : 'false');
        });
    });

    document.addEventListener('click', function (e) {
        dropdowns.forEach(dropdown => {
            const dropbtn = dropdown.querySelector('.dropbtn');
            const dropdownContent = dropdown.querySelector('.dropdown-content');

            if (!dropdown.contains(e.target)) {
                dropdownContent.classList.remove('show');
                dropbtn.setAttribute('aria-expanded', 'false');
            }
        });
    });

    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') {
            dropdowns.forEach(dropdown => {
                const dropbtn = dropdown.querySelector('.dropbtn');
                const dropdownContent = dropdown.querySelector('.dropdown-content');
                dropdownContent.classList.remove('show');
                dropbtn.setAttribute('aria-expanded', 'false');
            });
        }
    });
});
