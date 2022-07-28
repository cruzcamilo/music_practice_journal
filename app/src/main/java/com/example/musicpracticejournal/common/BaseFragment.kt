package com.example.musicpracticejournal.common


import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.di.presentation.PresentationModule
import com.example.musicpracticejournal.screens.activities.BaseActivity

open class BaseFragment : Fragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(
            PresentationModule(this, this.arguments)
        )
    }

    protected val injector get() = presentationComponent

    fun setToolbar(toolbar: Toolbar?) {
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        toolbar?.setupWithNavController(findNavController(), appBarConfiguration)
        if (findNavController().previousBackStackEntry != null) {
            toolbar?.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        } else {
            toolbar?.navigationIcon = null
        }
        findNavController().addOnDestinationChangedListener { _, _, _ ->
            if (toolbar?.navigationIcon != null) toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        }
    }

    fun safeNavigate(directions: NavDirections, extras: FragmentNavigator.Extras? = null) {
        val controller = findNavController()
        val currentDestination =
            (controller.currentDestination as? FragmentNavigator.Destination)?.className
                ?: (controller.currentDestination as? DialogFragmentNavigator.Destination)?.className
        if (currentDestination == this.javaClass.name) {
            extras?.let { controller.navigate(directions, it) } ?: controller.navigate(directions)
        }
    }
}