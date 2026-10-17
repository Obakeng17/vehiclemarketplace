document.addEventListener("DOMContentLoaded", () => {

    const searchInput = document.getElementById("searchInput");
    const brandFilter = document.getElementById("brandFilter");
    const modelFilter = document.getElementById("modelFilter");
    const vehicleCards = document.querySelectorAll(".vehicle-card");

    // Store unique categories
    const categories = new Set();

    vehicleCards.forEach(card => {
        if (card.dataset.category) {
            categories.add(card.dataset.category);
        }
    });

    // Populate Category Dropdown
    function updateModelDropdown() {

        modelFilter.innerHTML =
            '<option value="">All Categories</option>';

        [...categories]
            .sort()
            .forEach(category => {

                const option = document.createElement("option");

                option.value = category.toLowerCase();
                option.textContent = category;

                modelFilter.appendChild(option);

            });

    }

    // Filter Vehicles
    function filterVehicles() {

        const searchValue = searchInput.value.toLowerCase().trim();
        const selectedBrand = brandFilter.value.toLowerCase();
        const selectedCategory = modelFilter.value.toLowerCase();

        vehicleCards.forEach(card => {

            const brand = card.dataset.brand.toLowerCase();
            const model = card.dataset.model.toLowerCase();
            const category = card.dataset.category.toLowerCase();

            // Search by brand OR model
            const matchesSearch =
                searchValue === "" ||
                brand.includes(searchValue) ||
                model.includes(searchValue);

            // Brand filter
            const matchesBrand =
                selectedBrand === "" ||
                brand === selectedBrand;

            // Category filter
            const matchesCategory =
                selectedCategory === "" ||
                category === selectedCategory;

            card.style.display =
                (matchesSearch &&
                 matchesBrand &&
                 matchesCategory)
                    ? "block"
                    : "none";

        });

    }

    // Events
    searchInput.addEventListener("input", filterVehicles);

    brandFilter.addEventListener("change", filterVehicles);

    modelFilter.addEventListener("change", filterVehicles);

    // Initialize
    updateModelDropdown();
    filterVehicles();

});