(function () {
    'use strict';

    document.addEventListener('DOMContentLoaded', function () {
        const header   = document.querySelector('.site-header');
        if (!header) return;

        const nav      = header.querySelector('.site-nav');
        const hamburger = header.querySelector('.hamburger');

        // ── Hamburger Toggle ────────────────────────────────
        if (hamburger && nav) {
            hamburger.addEventListener('click', function () {
                const open = nav.classList.toggle('mobile-open');
                hamburger.classList.toggle('open', open);
                hamburger.setAttribute('aria-expanded', String(open));
            });
        }

        // ── Level-1 Dropdown Toggles ────────────────────────
        const navToggles = header.querySelectorAll('.nav-toggle');
        navToggles.forEach(function (toggle) {
            toggle.addEventListener('click', function (e) {
                e.stopPropagation();
                const navItem = toggle.closest('.nav-item');
                const isOpen  = navItem.classList.contains('open');

                // Close all other nav dropdowns
                closeAllNavDropdowns(header);

                if (!isOpen) {
                    navItem.classList.add('open');
                    toggle.setAttribute('aria-expanded', 'true');
                }
            });
        });

        // ── Level-2 Sub-Panel Toggles ───────────────────────
        const subToggles = header.querySelectorAll('.sub-toggle');
        subToggles.forEach(function (toggle) {
            toggle.addEventListener('click', function (e) {
                e.stopPropagation();
                const dropItem = toggle.closest('.dropdown-item');
                const isOpen   = dropItem.classList.contains('sub-open');

                // Close siblings
                const siblings = dropItem.parentElement.querySelectorAll('.dropdown-item.sub-open');
                siblings.forEach(s => {
                    s.classList.remove('sub-open');
                    s.querySelector('.sub-toggle')?.setAttribute('aria-expanded', 'false');
                });

                if (!isOpen) {
                    dropItem.classList.add('sub-open');
                    toggle.setAttribute('aria-expanded', 'true');
                }
            });

            // Also open sub-panel on hover (desktop UX)
            const dropItem = toggle.closest('.dropdown-item');
            dropItem.addEventListener('mouseenter', function () {
                if (window.innerWidth > 900) {
                    const siblings = dropItem.parentElement.querySelectorAll('.dropdown-item.sub-open');
                    siblings.forEach(s => s.classList.remove('sub-open'));
                    dropItem.classList.add('sub-open');
                }
            });
            dropItem.addEventListener('mouseleave', function () {
                if (window.innerWidth > 900) {
                    dropItem.classList.remove('sub-open');
                }
            });
        });

        // ── Close on outside click ───────────────────────────
        document.addEventListener('click', function () {
            closeAllNavDropdowns(header);
        });

        // ── Close on Escape ─────────────────────────────────
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Escape') closeAllNavDropdowns(header);
        });

        // ── Helpers ─────────────────────────────────────────
        function closeAllNavDropdowns(scope) {
            scope.querySelectorAll('.nav-item.open').forEach(function (item) {
                item.classList.remove('open');
                item.querySelector('.nav-toggle')?.setAttribute('aria-expanded', 'false');
                // Also close sub-panels inside
                item.querySelectorAll('.dropdown-item.sub-open').forEach(function (sub) {
                    sub.classList.remove('sub-open');
                });
            });
        }
    });

})();